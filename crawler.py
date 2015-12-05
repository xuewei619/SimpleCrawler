
import threading
import queue
import re
import urllib.request


q = queue.Queue()
img = []
l = []
count = 0
init_url = "http://www.acfun.tv"
q.put(init_url)
l.append(init_url)

lock = threading.Lock()
class test(threading.Thread):
	def __init__(self,name):
		threading.Thread.__init__(self)
		self.name = name

	def run(self):
		global q
		global l
		global init_url
		global count
		global img
		while True:	
			try:
				lock.acquire()
				url = q.get()
				count += 1
				print(count)						
				lock.release()
				print(self.name,url)					
				response = urllib.request.urlopen(url,timeout=1)
				html = response.read()	
				html = html.decode('GBK')
				reg = r'<[img|IMG][\s\S]*?src="([\s\S]+?)"[\s\S]*?>'
				p = re.compile(reg)				
				m = p.findall(html)				
				if m:					
					for img_url in m:						
						if img_url not in img:	
							print(img_url)						
							img.append(img_url)
							picture = img_url
							if re.match(r'http://',img_url):
								pass
							else:
								picture = init_url + img_url						
							index = picture.rfind("/")
							name = picture[index+1:len(picture)]
							try:
								response = urllib.request.urlopen(picture,timeout = 1)
								res = response.read()
								f = open("D:/img/"+name,'wb')
								f.write(res)
							except Exception as e:
								continue
								raise
							else:
								pass
							finally:
								pass

			except Exception as e:
				continue
				raise
			else:
				regex = r'<[\s\S]+?href="([\s\S]+?)"[\s\S]*?>[\s\S]*?</[\s\S]+?>'
				pattern = re.compile(regex)
				ma = pattern.findall(html)
				if ma:
					for obj in ma:						
						if obj not in l:
							#new_url = obj
							if re.match(r'http://',obj):
								#new_url = obj
								print('hehe');
							else:
								new_url = init_url + obj
								q.put(new_url)
								l.append(new_url)
					

			finally:
				pass
		
if __name__ == '__main__':
	thread1 = test('A')
	thread2 = test('B')
	thread3 = test('C')
	thread4 = test('D')
	thread1.start()
	thread2.start()
	thread3.start()
	thread4.start()

	




