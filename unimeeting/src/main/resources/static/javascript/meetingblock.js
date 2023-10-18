let meetings = {'idx':1, 'title':"미팅 제목1", "category":"카테고리1","location":'위치1',"start_date":"2023-10-17 14:00:00","content_text":'미팅 내용 1',
          "create_datetime":"2023-10-16 15:00:00", "writer_nickname":'사용자1', "recruits":5};

function makeMeetingBlock(meeting) {
  const div = document.createElement('div');
  div.id = "meeting_div";
  
  const img_div = document.createElement('div');
  img_div.id = "img_div";

  const img = document.createElement('img');
  img.id = "meeting_img";
  img.width = 100;
  img.height = 100;
  img.src = meeting.img_src;
  img_div.appendChild(img);

  const content_div = document.createElement('div');
  content_div.id = "content_div";

  
  const info = document.createElement('h5');
  info.id ="Title_Category"
  info.classList.add("meeting-info"); // h5에 class 추가

  const category = document.createElement('span');
  category.textContent = meeting.category;
  category.classList.add("meeting-info"); // span에 class 추가

  const location = document.createElement('span');
  location.textContent = meeting.location;
  location.id = "location"
  location.classList.add("meeting-info"); // span에 class 추가

  info.appendChild(category);
  info.appendChild(document.createTextNode(' ')); // 공백을 추가하여 간격을 만듭니다.
  info.appendChild(location);

  const title = document.createElement('h2');
  title.textContent = meeting.title;
  title.classList.add("meeting-info"); // h4에 class 추가

  const content_text = document.createElement('h5');
  content_text.textContent = meeting.content_text;
  content_text.id = "meeting_content";
  content_text.classList.add("meeting-info"); // h5에 class 추가

  const recruits = document.createElement('div');
  recruits.textContent = meeting.now_recruits + "/" + meeting.recruits;
  recruits.classList.add("meeting-info", "user-card"); // h6에 class 추가


  
  const create_datetime = document.createElement('div');
  create_datetime.textContent = meeting.create_datetime;
  create_datetime.classList.add("meeting-info", "user-thumb"); // h6에 class 추가
  
  const writer_nickname = document.createElement('div');
  writer_nickname.textContent = meeting.writer_nickname;
  writer_nickname.classList.add("meeting-info", "details"); // h6에 class 추가
  
  const wirter_time = document.createElement('div');
  wirter_time.id = "flowchild";
  writer_nickname.classList.add("meeting-info");
  wirter_time.appendChild(create_datetime);
  wirter_time.appendChild(writer_nickname);

  const flowdiv = document.createElement('div');
  flowdiv.id="flow"

  flowdiv.appendChild(recruits);
  flowdiv.appendChild(wirter_time);

  content_div.appendChild(info);
  content_div.appendChild(title);
  content_div.appendChild(content_text);
  content_div.appendChild(flowdiv);

  div.appendChild(img_div);
  div.appendChild(content_div);

  document.getElementById('info_result').appendChild(div);
}

export { makeMeetingBlock }