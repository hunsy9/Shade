<template>
  <div class="zidx" @click="this.$emit('closeModalServerListItem')">
    <div class="modal">
      <div class="overlay">
        <div class="modal-card" :style="{top: this.locationY + 'px', left: this.locationX + 'px'}">
          <div class="content" @click="$emit('openModalServerInfo', server.server_name, server.server_desc)">
            Info
            <img src="@/assets/modalServerListItem-info.png" width="13" height="13" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>
          </div>
<!--          <div class="content">-->
<!--            Version Track-->
<!--            <img src="@/assets/modalServerListItem-edit.png" width="15" height="15" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>-->
<!--          </div>-->
<!--          <div class="content" v-if="isAdmin">-->
<!--            Edit-->
<!--            <img src="@/assets/modalServerListItem-edit.png" width="15" height="15" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>-->
<!--          </div>-->
          <div class="content" v-if="isAdmin" @click="$emit('openDeleteModal', DeleteState, {server_id: server.server_id})">
            Delete
            <img src="@/assets/modalServerListItem-delete.png" width="13" height="13" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";
import {DeleteState} from "@/store/inOrganization";

export default {
  name: "ModalAdminServerListItem",
  props:{
    locationX: Number,
    locationY: Number,
    server: Object
  },
  computed:{
    ...mapState('inOrganization', {
      isAdmin: state => state.isAdmin
    }),
  },
  data(){
    return{
      DeleteState: DeleteState.DELETESERVER,
    }
  }
}
</script>

<style scoped>
.modal{
  position: relative;
  animation: fade-in-down 0.1s;
}
.overlay {
  width: 100%;
  height: 100%;
}
.modal-card {
  position: absolute;
  display: flex;
  flex-direction: column;
  max-width: 300px;
  min-height: 50px;
  z-index: 999;
  background-color: #383838;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fade-in-down 0.5s;
}

.content {
  color: #ffffff;
  font-size: 13px;
  border-radius: 0.5rem;
  display: flex;
  padding-top: 2px;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  justify-content: space-between;
  line-height: 170%;
  text-align: center;
  height: 1.7rem;
  cursor: pointer;
}

.content:hover {
  background-color: #4b4b4b;
}

@keyframes fade-in-down {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>