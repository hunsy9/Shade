<template>
  <button class="button" @click="openAddL1Category">
    Add New Category
  </button>
</template>
    
<script>
import {ActionCategoryState} from "@/store/inOrganization";
import {mapState} from "vuex";

export default {
  name: 'NewCategoryButton',
  computed:{
    ...mapState("inOrganization",{
      org_id : state => state.organId,
      project_id: state => state.selected_proj_id
    })
  },
  data(){
    return{
      AddCategoryState: ActionCategoryState.ADDL1,
    }
  },
  methods:{
    openAddL1Category(){
      this.$emit('openAddCategoryModal', this.AddCategoryState, {"org_id" : this.org_id, "project_id" : this.project_id})
    },
  }
}
</script>

<style scoped>
.button{
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translateX(-50%);
  color: var(--ModeTextColor);
  display: block;
  margin: 0 auto;
  background-color: var(--ModeBtnColor);
  padding-left: 1.3rem;
  padding-right: 1.3rem;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  animation: fade-in 1.5s linear;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>