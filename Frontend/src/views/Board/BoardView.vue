<script setup>
import {onMounted, ref, watch} from "vue";
import axios from "axios";
import BoardItem from "@/components/Board/BoardItem.vue";
import {useRoute, useRouter} from "vue-router";
import Pagenation from "@/components/Pagenation.vue";

const route = useRoute();
const router = useRouter();

const goToDetail = (boardId) => {
  router.push(`/board/${boardId}`);
  console.log(boardId);
};

const goToWrite = () => {
  router.push('/board/write');
}

const items = ref([{}]);

const page = ref(1);

const searchOptions = ref([
  {text: '제목', value: 'title'},
  {text: '내용', value: 'content'},
  {text: '글쓴이', value: 'memberId'},
]);

const isSearched = ref(false);
const fetchDataFromServer = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/board?pageNo=${page.value}`);
    items.value = response.data;
    console.log("페이지 성공적으로 불러왔습니다.")
    isSearched.value = false;
  } catch (error) {
    console.error('데이터를 불러오는데 실패했습니다.', error);
  }
};

watch(page, () => {
  if (isSearched.value) {
    searchBoard();
  } else {
    fetchDataFromServer();
  }
});

const selectedOption = ref(searchOptions.value[0].value);
const searchKeyword = ref(''); // 사용자가 입력한 검색어를 저장할 변수

const totalItems = ref(0);

const searchBoard = async () => {
  try {
    const response = await axios.get('http://localhost:8080/board/search', {
      params: {
        searchType: selectedOption.value,
        keyword: searchKeyword.value,
        pageNo: page.value,
        pageSize: 8
      },
    });
    console.log("검색이 성공했습니다.")
    items.value = response.data.content;
    totalItems.value = response.data.totalElements;
    isSearched.value = true;
  } catch (error) {
    console.error('검색 요청 중 오류가 발생했습니다.', error);
  }
};

onMounted(async () => {
  const response = await axios.get('http://localhost:8080/board/count');
  console.log(response.data);
  totalItems.value = response.data;
  fetchDataFromServer();
});

</script>

<template>
  <div class="red Jalnan ps-5">
    <v-container>
      <h1>게시판</h1>
      다양한 방문 후기를 즐기세요~
    </v-container>
  </div>
  <v-container>

    <v-row no-gutters class="mt-4">

      <v-col class="mt-5 pl-3 d-flex justify-start align-center" :cols="2" offset="2">
        <v-select :items="searchOptions" v-model="selectedOption"
                  label="검색 옵션" item-title="text" item-value="value"
                  color="red-accent-3" variant="outlined" class="search-option me-5"></v-select>
      </v-col>
      <v-col class="d-flex justify-start align-center" :cols="4">
        <v-text-field v-model="searchKeyword" hide-details color="red-accent-3" label="게시판 검색" variant="outlined"
                      class="search-box"></v-text-field>
      </v-col>
      <v-col class="d-flex justify-start align-center" :cols="2">
        <v-btn class="search-btn Jalnan" @click="searchBoard">검색</v-btn>
      </v-col>
      <v-col class="d-flex align-center justify-center" :cols="2">
        <v-btn class="write-btn Jalnan" @click="goToWrite">글쓰기</v-btn>
      </v-col>

    </v-row>
    <v-row class="mb-2">

    </v-row>
    <v-row>
      <v-col v-for="(item, index) in Array(8).fill().map((_, i) => items[i] || {})" :key="index" :cols="3">
        <div v-if="item.boardId" @click="goToDetail(item.boardId)">
          <BoardItem :title="item.title" :memberId="item.memberId"/>
        </div>
      </v-col>
    </v-row>
    <Pagenation v-model="page" :totalItems="totalItems"/>
  </v-container>
</template>

<style scoped>

th {
  font-family: Jalnan
}


.search-btn {
  height: 56px;
  width: 60px;
  background-color: rgb(247, 50, 63);
  color: rgb(255, 255, 255);
  caret-color: rgb(255, 255, 255);
}

.write-btn {
  background-color: rgb(247, 50, 63);
  color: rgb(255, 255, 255);
  caret-color: rgb(255, 255, 255);
}
</style>
