<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="btnWrapper">
          <button @click="$emit('closeDeleteModal')">Cancle</button>
          <button @click="deleteController"> Ok</button>
          <div>{{ DeleteState + DeleteDto }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {DeleteState} from "@/store/inOrganization";
import deleteApi from "../common/api/deleteApi";

export default {
  name: "DeleteModal",
  props: {
    DeleteState: DeleteState,
    DeleteDto: Object
  },
  methods: {
    deleteController() {
      switch (this.DeleteState) {
        case DeleteState.DELETEPROJECT:
          deleteApi.deleteProject(this.DeleteDto)
          break
        case DeleteState.DELETECONTRIBUTOR:
          deleteApi.deleteContributor(this.DeleteDto)
          break
        case DeleteState.DELETESERVER:
          console.log(this.DeleteDto)
          deleteApi.deleteServer(this.DeleteDto)
          break
        case DeleteState.DELETECATEGORYL1:
          deleteApi.deleteCategoryL1(this.DeleteDto)
          console.log(this.DeleteState)
          break
        case DeleteState.DELETECATEGORYL2:
          deleteApi.deleteCategoryL2(this.DeleteDto)
          break
        default:
          break
      }
      this.$emit('closeDeleteModal')
    }
  }
}
</script>

<style scoped>
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}

.modal-card {
  position: relative;
  max-width: 475px;
  min-height: 230px;
  margin: auto;
  margin-top: 270px;
  padding: 20px;
  background-color: white;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}

input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #565656;
  width: 17rem;
  border: none;
  outline: none;
}
</style>