import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/MainRounge.vue'
import meeting from '@/pages/Meeting.vue'
import mypage from '@/pages/Mypage.vue'
import meetingView from '@/pages/MeetingView.vue'
//Board
import Free from '@/pages/Board/Free.vue'
import Notice from '@/pages/Board/Notice.vue'
import Detail from '@/pages/Board/Detail.vue'
import Write from '@/pages/Board/Write.vue'
//User
import Register from '@/pages/User/Register.vue'
import Login from '@/pages/User/Login.vue'

const router = createRouter({
    history: createWebHistory(),
    routes : [
        { path: '/', component: Home },
        { path: '/meetings', component: meeting },
        // { path: '/join', component: join },
        { path: '/mypage', component: mypage},
        { path: '/meeting/:idx', component: meetingView},
        
        { path: '/boards/type/free', component: Free },
        { path: '/boards/type/notice', component: Notice },
        { path: '/boards/:idx', component: Detail },
        { path: '/boards/:type/write', component: Write},
        { path: '/user/register', component:Register},
        { path: '/user/login', component: Login },
        // { path: '/boards/type/notice', component: Notice },
        // { path: '/boards/:idx', component:BoardDetail},
    ]
})

export default router;