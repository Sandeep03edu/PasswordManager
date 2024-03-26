from flask import Flask, request, jsonify
# import os
from langchain_community.document_loaders import TextLoader
import textwrap
from langchain.text_splitter import CharacterTextSplitter
from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import FAISS
from langchain.chains.question_answering import load_qa_chain
from langchain_community.llms.huggingface_endpoint import HuggingFaceEndpoint
# import Constants
from waitress import serve


app = Flask(__name__)

def wrap_text_preserve_new_lines(text, width=110):
    lines = text.split('\n')
    wrapped_lines = [textwrap.fill(line, width=width) for line in lines]
    wrapped_text = '\n'.join(wrapped_lines)
    return wrapped_text

def process_query(query):
    # os.system("cls")

    try:

        loader = TextLoader("./data/app.txt")
        document = loader.load()
        # os.environ["HUGGINGFACEHUB_API_TOKEN"] = Constants.TOKEN


        wrapped_text = wrap_text_preserve_new_lines(str(document[0]))

        # Split the document into chunks
        text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
        docs = text_splitter.split_documents(document)

        # Create embeddings
        embedding = HuggingFaceEmbeddings()
        db = FAISS.from_documents(docs, embedding)

        # Load the Question-Answering chain
        llm = HuggingFaceEndpoint(repo_id="google/flan-t5-xxl", temperature=0.8, model_kwargs={"max_length": 512})
        chain = load_qa_chain(llm, chain_type="stuff")
        querySimilarDocs = db.similarity_search(query)

        res = chain.run(input_documents = querySimilarDocs, question = query)

        return res
    except Exception as e:
        print("Error: ", e)
        return "An Error occurred!!"

@app.route('/query', methods=['POST'])
def process_request():
    data = request.get_json()
    user_input = data['query']
    response = process_query(user_input)
    return jsonify({"response": response})

if __name__ == '__main__':
    # app.run(debug=True)
    serve(app, host="0.0.0.0", port=8080)
