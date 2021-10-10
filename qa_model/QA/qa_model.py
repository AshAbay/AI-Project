from happytransformer import HappyQuestionAnswering

def retrieve_answer_from_context(context, question):
    happy_qa_bert = HappyQuestionAnswering('ALBERT', 'mfeb/albert-xxlarge-v2-squad2')
    result = happy_qa_bert.answer_question(context, question, top_k=2)
    print(type(result))  # <class 'list'>
    print(result)  # [QuestionAnsweringResult(answer='January 10th, 2021', score=0.9711642265319824, start=16, end=34), QuestionAnsweringResult(answer='January 10th', score=0.017306014895439148, start=16, end=28)]
    print(result[1].answer)  # January 10th

def retrieve_answer():
    context="WhatsApp Messenger, or simply WhatsApp, is an American freeware, cross-platform centralized instant messaging and voice-over-IP service owned by Facebook, Inc. It allows users to send text messages and voice messages, make voice and video calls, and share images, documents, user locations, and other content."
    question = "What is whatsapp?"
    retrieve_answer_from_context(context, question)
