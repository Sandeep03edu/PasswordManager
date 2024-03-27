try:
    import os
    import sys
    import json
    from langchain_community.document_loaders import TextLoader
    from langchain.text_splitter import CharacterTextSplitter
    from langchain_community.embeddings import HuggingFaceEmbeddings
    from langchain_community.vectorstores import FAISS
    from langchain.chains.question_answering import load_qa_chain
    from langchain_community.llms.huggingface_endpoint import HuggingFaceEndpoint
    import warnings

    # Disable all warnings
    warnings.filterwarnings("ignore")

    # Read queries from standard input and print responses to standard output
    inp = sys.stdin
    for line in sys.stdin:
        if '#080819082709#' in line.strip():
            tokens = line.split('#080819082709#')

            # Load the text document
            loader = TextLoader("controller\\data\\app.txt")
            document = loader.load()
            os.environ["HUGGINGFACEHUB_API_TOKEN"] = tokens[1].strip()

            # Split the document into chunks
            text_splitter = CharacterTextSplitter(chunk_size=1000, chunk_overlap=0)
            docs = text_splitter.split_documents(document)

            # Create embeddings
            embedding = HuggingFaceEmbeddings()
            db = FAISS.from_documents(docs, embedding)

            # Load the Question-Answering chain
            llm = HuggingFaceEndpoint(repo_id="google/flan-t5-xxl", temperature=0.8, model_kwargs={"max_length": 512})
            chain = load_qa_chain(llm, chain_type="stuff")
        else:
            query = line.strip()

            # Perform similarity search
            querySimilarDocs = db.similarity_search(query)

            # Get the response
            res = chain.run(input_documents=querySimilarDocs, question=query)

            # Print the response to standard output
            print(json.dumps( "##080819082709##"+ res))
            sys.stdout.flush()  # Flush the output buffer to ensure response is sent immediately
except Exception as err:
    print(json.dumps( "Error:: " + err))
    sys.stdout.flush()  # Flush the output buffer to ensure response is sent immediately