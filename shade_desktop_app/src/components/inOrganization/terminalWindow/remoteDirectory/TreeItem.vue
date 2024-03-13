<script setup>
import { ref, computed, defineProps } from 'vue'

const props = defineProps({
  model: Object
})

const isOpen = ref(false)
const isFolder = computed(() => {
  return props.model.children && props.model.children.length
})

const toggle = () => {
  isOpen.value = !isOpen.value
}

const changeType = (name) => {
  if (!isFolder.value) {
    // eslint-disable-next-line vue/no-mutating-props
    // props.model.children = []
    // addChild()
    // isOpen.value = true
    alert("Move To /"+ name)
  }
}

const addChild = () => {
  // eslint-disable-next-line vue/no-mutating-props
  props.model.children.push({ name: 'new stuff' })
}
</script>

<template>
  <li>
    <div
        :class="{ bold: isFolder }"
        @dblclick="changeType(model.name)">
      {{ model.name }}
      <img class="dotSign" @click="toggle" :src="isOpen ? require('@/assets/downArrow.png') : require('@/assets/rightArrow.png')" width="6"/>
<!--      <span v-if="isFolder">[{{ isOpen ? '-' : '+' }}]</span>-->
    </div>
    <ul v-show="isOpen" v-if="isFolder">
<!--      <TreeItem-->
<!--          class="item"-->
<!--          v-for="model in model.children"-->
<!--          :model="model">-->
<!--      </TreeItem>-->
      <li class="add" @click="addChild">+</li>
    </ul>
  </li>
</template>

<style>
.dotSign{
  margin-left: 5px;
}
</style>