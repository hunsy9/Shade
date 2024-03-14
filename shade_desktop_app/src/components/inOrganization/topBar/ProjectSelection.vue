<template>
  <div class="projectSelection"  @click="openSelectionModal" :class="{'projectSelection-shifted': shiftState==true}">
    <span class="pName">
      {{proj}}
    </span>
    <div class="sort-vertical-wrapper">
      <img src="@/assets/sort-vertical.png" width="14"/>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('inOrganization')

export default {
  name: "ProjectSelection",
  props:{
    shiftState: Boolean,
  },
  computed: {
    ...mapState({
      proj: state => state.selected_proj,
    }),
  },
  methods:{
    openSelectionModal(){
      this.$emit("openProjectSelectionModal")
    }
  }
}
</script>

<style scoped>
.projectSelection{
  cursor: pointer;
  margin-left: 2rem;
  padding-left: 0.2rem;
  height: 60%;
  width: 95px;
  border-radius: 0.2rem;
  margin-right: 30rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
.projectSelection:hover{
  background-color: #262626;
}
.projectSelection-shifted{
  margin-left: 7rem;
}
.sort-vertical-wrapper{
  z-index: 99;
  margin-left: 0.6rem;
  display: flex;
  align-items: center;
}
.pName{
  font-size: 0.85rem;
  color: var(--ModeTextColor);
}
.pName-shifted{
  left: 7rem;
  font-size: 0.85rem;
  color: var(--ModeTextColor);
}
</style>