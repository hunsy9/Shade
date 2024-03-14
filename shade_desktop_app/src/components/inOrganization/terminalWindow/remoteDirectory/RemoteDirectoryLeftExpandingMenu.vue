<script setup>
// import {ref} from "vue";
// import TreeItem from './TreeItem.vue'

// const treeData = ref({
//   name: 'EZON : root',
//   children: [
//     { name: 'home' },
//     { name: 'usr' },
//     {
//       name: 'bin',
//       children: [
//         {
//           name: 'media',
//           children: [{ name: 'hello' }, { name: 'world' }]
//         },
//         {
//           name: 'child folder',
//           children: [{ name: 'hello' }, { name: 'world' }]
//         }
//       ]
//     }
//   ]
// })

</script>

<template>
  <div class="remoteDirectoryWrapper">
    <div class="quickManage">
      Quick Manage
    </div>
<!--    <ul class="im1">-->
<!--      <TreeItem class="item" :model="treeData"></TreeItem>-->
<!--    </ul>-->
    <div class="dev">
      Beta
    </div>
  </div>
  <div class="versionTracking">
    Version Tracking
  </div>
  <div class="dev">
    Beta
  </div>
</template>

<style scoped>
.remoteDirectoryWrapper{
  height: 30px;
  max-height: 10rem;
  padding-top: 50px;
  justify-content: center;
  color: white;
}
.remoteDirectoryName{
  padding-left: 20px;
  font-size: 13px;
  width: 100%;
  background-color: #151825;
}
.item {
  cursor: pointer;
  line-height: 1.5;
  color: white;
  font-size: 13px;
}
ul.im1{
  list-style-image: url( '@/assets/folder.png' );
}
.quickManage{
  line-height: 30px;
  height: 30px;
  margin-left: 1rem;
  color: white;
}

.versionTracking{
  margin-top: 5rem;
  line-height: 30px;
  height: 30px;
  margin-left: 1rem;
  color: white;
}
.dev{
  margin-top: 1rem;
  background-color: #1A1A1A;
  border-radius: 0.7rem;
  height: 3rem;
  text-align: center;
  line-height : 3rem;
  color: #6F6A6A;
}
</style>