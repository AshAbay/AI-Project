import librosa
import torch
from transformers import Wav2Vec2ForCTC, Wav2Vec2Tokenizer


# Improvements:
# - gpu / cpu flag
# - convert non 16 khz sample rates
# - inference time log
class Wave2Vec2Inference():
    def __init__(self,model_name):
        self.tokenizer = Wav2Vec2Tokenizer.from_pretrained("facebook/wav2vec2-base-960h")
        self.model = Wav2Vec2ForCTC.from_pretrained(model_name)

    def buffer_to_text(self,audio_buffer):
        if(len(audio_buffer)==0):
            return ""
        input_values = self.tokenizer(audio_buffer, return_tensors="pt").input_values

        with torch.no_grad():
            logits = self.model(input_values).logits

        prediction = torch.argmax(logits, dim=-1)
        transcription = self.tokenizer.batch_decode(prediction)[0]
        return transcription.lower()

    def file_to_text(self,filename):
        audio, rate = librosa.load(filename, sr=16000)
        return self.buffer_to_text(audio)

if __name__ == "__main__":
    asr = Wave2Vec2Inference("facebook/wav2vec2-base-960h")
    text = asr.file_to_text("sample1.flac")
    print(text)