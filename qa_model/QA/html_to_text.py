import html2text
from urllib.request import Request, urlopen
from bs4 import BeautifulSoup
from happytransformer import HappyQuestionAnswering
import torch
from datetime import datetime

def download_html(url):
    file_name = "temporary_google_html.html"
    hdr = {
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3',
        'Accept-Encoding': 'none',
        'Accept-Language': 'en-US,en;q=0.8',
        'Connection': 'keep-alive'}
    req = Request(url, headers=hdr)
    # with urlopen(req) as response, open(file_name, 'wb') as out_file:
    #     shutil.copyfileobj(response, out_file)
    response = urlopen(req)
    data = response.read()
    return data

def htmltoText(url):
    response = download_html(url)
    # h = html2text.HTML2Text()
    # h.ignore_links = True
    # print(h.handle(response))
    soup = BeautifulSoup(response)
    print(soup.get_text())
    return soup.get_text()

def retrieve_answer_from_context(context, question):
    happy_qa_bert = HappyQuestionAnswering('ALBERT', 'mfeb/albert-xxlarge-v2-squad2')
    print ("Cuda is available: ")
    print (torch.cuda.is_available())

    now = datetime.now()

    current_time = now.strftime("%H:%M:%S")
    print("Current Time =", current_time)
    result = happy_qa_bert.answer_question(context, question, top_k=2)
    now = datetime.now()

    current_time = now.strftime("%H:%M:%S")
    print("Current Time =", current_time)
    print("\n\n\nGOT ANSWER\n\n\n")
    print(type(result))  # <class 'list'>
    print(result)  # [QuestionAnsweringResult(answer='January 10th, 2021', score=0.9711642265319824, start=16, end=34), QuestionAnsweringResult(answer='January 10th', score=0.017306014895439148, start=16, end=28)]
    print(result[1].answer)  # January 10th

if __name__ == "__main__":
    print("Live ASR")
    context = htmltoText("https://en.wikipedia.org/wiki/Marshall_(film)#Cast")
    question = "who acted in the movie marshall"
    retrieve_answer_from_context(context, question)
