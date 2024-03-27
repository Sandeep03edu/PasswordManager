from django.urls import path

from . import views

urlpatterns = [
    path("query/", views.process_query, name="process query")
]