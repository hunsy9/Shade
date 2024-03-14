<template>
  <div class="zidx" @click="this.$emit('closeProjectOptionModal')">
    <div class="modal">
      <div class="overlay">
        <div class="modal-card" :style="{top: this.locationY + 'px', left: this.locationX + 'px'}">
          <div class="content">
            Edit
            <img src="@/assets/modalServerListItem-edit.png" width="15" height="15" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>
          </div>
          <!--          <div class="content" v-if="isAdmin">-->
          <!--            Edit-->
          <!--            <img src="@/assets/modalServerListItem-edit.png" width="15" height="15" style="padding-top: 3px; padding-left: 10px; padding-right: 5px;"/>-->
          <!--          </div>-->
          <div class="content" v-if="isAdmin">
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

export default {
  name: "ModalProjectOption",
  props:{
    locationX: Number,
    locationY: Number,
    project: Object
  },
  computed:{
    ...mapState('inOrganization', {
      isAdmin: state => state.isAdmin
    }),
  },
  mounted() {
    console.log("mounted!!: ", this.locationY)
  }
}
</script>

<style scoped>
.zidx {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  //z-index: 1000;
}
.modal{
  //position: relative;
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
  z-index: 10;
  background-color: #f6f6f6;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fade-in-down 0.5s;
}

.content {
  color: #4f4f4f;
  font-size: 13px;
  border-radius: 0.5rem;
  display: flex;
  padding-top: 2px;
  padding-left: 4px;
  padding-right: 2px;
  justify-content: space-between;
  line-height: 170%;
  text-align: center;
  height: 1.7rem;
  cursor: pointer;
}

.content:hover {
  background-color: #efefef;
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