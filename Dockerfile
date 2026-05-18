FROM ubuntu:latest
LABEL authors="jhpk"

ENTRYPOINT ["top", "-b"]