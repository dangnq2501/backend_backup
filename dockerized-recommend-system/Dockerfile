FROM python:3.10.9

WORKDIR /app

COPY requirements.txt ./
RUN pip install -r requirements.txt
RUN pip install fastapi uvicorn

COPY dockerized-recommend-system/app.py ./
COPY dockerized-recommend-system/. /app


EXPOSE 8000

CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "8000"]

#FROM python:3.10.9
#
#WORKDIR /app
#
#COPY requirements.txt ./
#RUN pip install -r requirements.txt
#
#COPY . /app
#
#CMD python recommend.py
