JULY-11-2022 Designed and edited by: Laurie Sylvester Jon Strand Amed Espinosa

This app provides an architecture for a functioning website that interacts with AWS DynamoDB utilizing the CRUD Repository interface.

The service implements findById, save, existsById, deleteById & findAll() from the CRUD Repository and uses these calls in the controller via the service.

The webpage makes local get, put, post and delete RESTful api calls to manipulate objects in a DynamoDB table. With AWS credentials this app has the potential to interact with the DynamoDB database when running on AWS services.

It can also be run with a local instance through a local Docker container. To run the local container, run the ./local-dynamodb.sh on the command line from the project's root directory.

Best used with AWS CLI.**

This app was designed for artists to upload, update, delete and view their own artwork as well as view others' uploaded works.

FUTURE FEATURE:
Upload a photo of the artwork to S3 on 

PLEASE NOTE:
This is a replication of the original product, some info has been changed.
Also please recognize the two other parties who were involved in the creation of this project(See top of README).
