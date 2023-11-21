<template>
  <hr />
  <BoardWrite :type="$route.params.type"></BoardWrite>
</template>

<script>
import BoardWrite from '@/components/BoardWrite.vue'

export default {
  components: {
    BoardWrite,
}
}
</script>

<style>
.study {
  color: blue;
}
</style>