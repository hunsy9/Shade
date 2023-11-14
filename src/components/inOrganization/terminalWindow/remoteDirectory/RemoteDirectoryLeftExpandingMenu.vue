<script setup>
import {ref} from "vue";
import TreeItem from './TreeItem.vue'

const treeData = ref({
  name: 'EZON : root',
  children: [
    { name: 'home' },
    { name: 'usr' },
    {
      name: 'bin',
      children: [
        {
          name: 'media',
          children: [{ name: 'hello' }, { name: 'world' }]
        },
        {
          name: 'child folder',
          children: [{ name: 'hello' }, { name: 'world' }]
        }
      ]
    }
  ]
})

</script>

<template>
  <div class="remoteDirectoryWrapper">
    <div class="quickManage">
      <img class="lightning" src="@/assets/remove-bg-shadeIcon.png" width="20"/>
      Quick Manage
    </div>
    <ul class="im1">
      <TreeItem class="item" :model="treeData"></TreeItem>
    </ul>
  </div>
  <div class="versionTracking">
    <img class="lightning" src="@/assets/remove-bg-shadeIcon.png" width="20"/>
    Version Tracking
  </div>
</template>

<style scoped>
.remoteDirectoryWrapper{
  height: 30px;
  max-height: 10rem;
  padding-top: 50px;
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
  display: flex;
  line-height: 30px;
  height: 30px;
  color: white;
}

.versionTracking{
  margin-top: 5rem;
  display: flex;
  line-height: 30px;
  height: 30px;
  color: white;
}
.lightning{
  margin-left: 1.2rem;
  margin-right: 0.7rem;
}
.bold {
  font-weight: bold;
}
</style>