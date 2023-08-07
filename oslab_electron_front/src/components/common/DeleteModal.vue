<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="header">
          <img src="@/assets/warning.png" width="35" height="35"/>
          <div style="padding-top: 10px; padding-left: 1px">Are you sure you want to delete it?</div>
        </div>
        <div class="btnWrapper">
          <button @click="$emit('closeDeleteModal')">Cancle</button>
          <button @click="deleteController"> Ok</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {DeleteState} from "@/store/inOrganization";
import {mapActions} from "vuex";

export default {
  name: "DeleteModal",
  props: {
    DeleteState: DeleteState,
    DeleteDto: Object
  },
  methods: {
    ...mapActions('inOrganization', ['refetchNewServer','deleteServer','deleteCategory', 'reFetchCategory','deleteContributor',"reFetchContributor"]),

    async deleteController() {
      switch (this.DeleteState) {
        case DeleteState.DELETEPROJECT:
          // deleteApi.deleteProject(this.DeleteDto)
          break
        case DeleteState.DELETECONTRIBUTOR:
          await this.deleteContributor(this.DeleteDto)
          await this.reFetchContributor()
          break
        case DeleteState.DELETESERVER:
          await this.deleteServer(this.DeleteDto)
          await this.refetchNewServer()
          break
        case DeleteState.DELETECATEGORYL1:
          await this.deleteCategory(this.DeleteDto)
          await this.reFetchCategory()
          break
        case DeleteState.DELETECATEGORYL2:
          await this.deleteCategory(this.DeleteDto)
          await this.reFetchCategory()
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
  width: 475px;
  height: 100px;
  margin: auto;
  margin-top: 270px;
  padding: 20px;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: warn 0.3s;
}

.header {
  text-align: center;
  justify-content: center;
  display: flex;
  color: white;
  margin-bottom: 30px;
  margin-top: 25px;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  width: 50px;
  color: white;
}

.btnWrapper {
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: space-between;
  position: relative;
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

@keyframes warn {
  30% {
    transform: scale(1.1);
  }
  40%,
  60% {
    transform: rotate(-3deg) scale(1.05);
  }
  50% {
    transform: rotate(3deg) scale(1.05);
  }
  70% {
    transform: rotate(0deg) scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

</style>