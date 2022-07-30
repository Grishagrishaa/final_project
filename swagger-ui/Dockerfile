FROM swaggerapi/swagger-ui:latest
MAINTAINER Grisha

ADD /classifier_service/spec/classifier_service.yaml /usr/share/nginx/html/doc/classifier_service.yaml
ADD /afisha_service/spec/afisha_service.yaml /usr/share/nginx/html/doc/afisha_service.yaml
ADD /user_service/spec/user_service.yaml /usr/share/nginx/html/doc/user_service.yaml


ENV URLS="[{ url: 'doc/classifier_service.yaml', name: 'Classifier'},{ url: 'doc/afisha_service.yaml', name: 'Afisha'},{ url: 'doc/user_service.yaml', name: 'User'}]"
ENV URLS_PRIMARY_NAME="Afisha"