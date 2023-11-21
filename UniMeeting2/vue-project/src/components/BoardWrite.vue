<template>
    <body>
        
        <div>
            <div class="container mt-5">
                <form @submit.prevent="writeBoard">
                    <label for="title">제목:</label>
                    <input v-model="title" type="text" class="form-control" required>
                    
                    <label for="content">내용:</label>
                    <textarea v-model="content" class="form-control" rows="5" required></textarea>
                    
                    <button type="submit" class="btn btn-primary">작성완료</button>
                </form>
                <div v-if="error" class="alert alert-danger mt-3">
                    {{ error }}
                </div>
            </div>
        </div>
    </body>
  </template>
  
  <script>
  import axios from 'axios';
  import { defineProps } from 'vue';
  export default {
    data() {
      return {
        title: '',
        content: '',
        error: null
      };
    },
    props : {
        type : String
    },
    methods: {
      async writeBoard() {
        // 작성된 게시글 데이터
        const postData = {
          title: this.title,
          content: this.content,
          type: this.type
        };
  
        try {
          await axios.post('http://localhost:8090/boards/write', postData);
  
          this.$router.go(-1); // 이전 페이지로 이동
        } catch (error) {
          console.error('게시글 작성 오류:', error);
          this.error = '게시글 작성 중 오류가 발생했습니다.';
        }
      },
    },
  };
  </script>
  
  <style>
  </style>
  