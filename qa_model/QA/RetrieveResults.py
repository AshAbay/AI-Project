from urllib.request import Request, urlopen
import requests
import urllib
from requests_html import HTMLSession

def download_html(rawQuery):
    url = "https://www.google.com/search?q=" + rawQuery
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

def get_source(url):
    """Return the source code for the provided URL.

    Args:
        url (string): URL of the page to scrape.

    Returns:
        response (object): HTTP response object from requests_html.
    """

    try:
        session = HTMLSession()
        response = session.get(url)
        return response

    except requests.exceptions.RequestException as e:
        print(e)

def retrieve_results(rawQuery):
    # data = download_html(rawQuery)
    # soup = BeautifulSoup(data, "html.parser")
    # main = soup.find("div", {"id": "main"})
    # kCrYTs = main.find_all("div", {"class": "kCrYT"})
    # for kCrYT in kCrYTs:
    #     # print(kCrYT.prettify())
    #     # for a in soup.select('div#main a[href]'):
    #     #     print(a['href'])
    #     my_divs = soup.find_all("div", {"class": "main"})
    #     my_links = [x.find_all("a", {"href": True}, recursive=False) for x in my_divs]
    #     # flatten
    #     my_links = [x for y in my_links for x in y]
    #     # extract hrefs
    #     my_links = [x["href"] for x in my_links]
    #     for link in my_links:
    #         print(link)

    print("\n\n\nUSING ANOTHER CODE\n\n\n")
    query = urllib.parse.quote_plus(rawQuery)
    response = get_source("https://www.google.co.uk/search?q=" + query)

    links = list(response.html.absolute_links)
    google_domains = ('https://www.google.',
                      'https://google.',
                      'https://webcache.googleusercontent.',
                      'http://webcache.googleusercontent.',
                      'https://policies.google.',
                      'https://support.google.',
                      'https://maps.google.')

    for url in links[:]:
        if url.startswith(google_domains):
            links.remove(url)
    for result_link in links:
        print(result_link)
    return links

    # print(main.prettify())
    #     links = kCrYT.find_all("a", href=True)
    #     for link in kCrYT.find_all('a', href=True):
    #         print(link['href'])
    # mw = cnt.find("div", {"class": "mw"})
    # print(mw.prettify())


if __name__ == "__main__":
    print("Live ASR")
    retrieve_results("who acted in the movie marshall")