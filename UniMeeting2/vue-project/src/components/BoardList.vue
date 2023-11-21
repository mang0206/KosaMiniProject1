<template>
  <div>
    <div class="container mt-5">
      <form @submit.prevent="search">
        <input v-model="searchText" type="text" class="form-control placeholder-image" placeholder="검색어를 입력해 주세요">
      </form>
      <table class="table table-bordered">
        <thead class="thead-dark">
          <tr>
            <th style="width: 150px">번호</th>
            <th>제목</th>
            <th style="width: 150px">작성자</th>
            <th style="width: 150px">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="board in boards" :key="board.id">
            <td style="text-align: center">{{ board.idx }}</td>
            <td>
              <router-link :to="{ path: `/boards/${board.idx}` }">{{ board.title }}</router-link>
            </td>
            <td style="text-align: center">{{ board.user.nickname }}</td>
            <td style="text-align: center">{{ board.createdDatetime }}</td>
          </tr>
        </tbody>
      </table>

      <div >
        <router-link to="/boards/free/write" class="btn btn-primary">게시글 작성</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { useBoardListStore } from '@/stores/boardliststore.js'; 

export default {
  data() {
    return {
      boardType: 'free', // 
    };
  },
  computed: {
    boards() {
      // 게시판 목록 스토어
      const boardListStore = useBoardListStore();

      // 현재 선택한 게시판 타입에 해당하는 게시판 글 목록을 반환.
      return boardListStore.boards.filter(board => board.type === this.boardType);
    },
  },
  methods: {
    // 게시판 글 목록 호출
    async fetchBoardList() {
      const boardListStore = useBoardListStore();
      await boardListStore.fetchBoardListByType(this.boardType);
    },
  },
  mounted() {
    // 마운트되면 가져옴.
    this.fetchBoardList();
  },
};
</script>
<style scoped>
@import "@/assets/css/boardList.css";
</style>
