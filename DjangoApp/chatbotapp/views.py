from django.shortcuts import render

# Create your views here.
from django.http import JsonResponse
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
import json
# import os
# import contants

@csrf_exempt
def process_query(request):
     if request.method == 'POST':
        data = json.loads(request.body)
        query = data['query']
        from langchain_community.document_loaders import TextLoader
        loader = TextLoader('chatbotapp/data/app.txt')
        documnet = loader.load()

        from langchain.text_splitter import CharacterTextSplitter
        text_splitter = CharacterTextSplitter(chunk_size = 1000, chunk_overlap=0)
        docs = text_splitter.split_documents(documnet)
      #   os.environ["HUGGINGFACEHUB_API_TOKEN"] = contants.TOKEN
        from langchain_community.embeddings import HuggingFaceEmbeddings
        from langchain_community.vectorstores import FAISS
        embedding = HuggingFaceEmbeddings()
        db = FAISS.from_documents(docs, embedding)

        querySimilarDocs = db.similarity_search(query)

        from langchain.chains.question_answering import load_qa_chain
        from langchain_community.llms.huggingface_endpoint import HuggingFaceEndpoint
        llm = HuggingFaceEndpoint(repo_id = "google/flan-t5-xxl", temperature = 0.8, model_kwargs = { "max_length" : 512})
        chain = load_qa_chain(llm, chain_type="stuff")
        res = chain.run(input_documents = querySimilarDocs, question = query)
        return JsonResponse({"Result" : res})
     else:
        return JsonResponse({'error': 'Only POST requests are allowed'}, status=400)