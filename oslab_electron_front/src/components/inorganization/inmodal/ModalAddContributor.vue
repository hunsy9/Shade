<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="planeWrapper">
          <img src="@/assets/paper-plane.png" width="40" height="40" class="paperPlane"/>
        </div>
        <div class="middle">
          Invite Contributor to {{organName}}_Organization
        </div>
        <div class="emailInfo">
          <label class="emailInput"> Email Address </label>
          <input type="text" />
        </div>

        <div class="btnWrapper">
          <button @click="$emit('closeInviteContributorModal')" class="cancel">Cancel</button>
          <button > Send</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers('inOrganization')
export default {
  name: 'ModalAddContributor',
  computed: {
    ...mapState({
      organName: state => state.organName.toUpperCase()
    }),
  },
  data() {
    return {
      addContributorEmail: '',
    }
  },
  components: {
  }
}
</script>

<style>
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
.emailInfo {
  text-align: center;
  margin-top: 3px;
  margin-bottom: 3px;

}

.emailInput{
  font-size: 0.8rem;
  padding-right: 2rem;
  color: white;
}

.planeWrapper{
  margin-top: 40px;
  margin-bottom: 20px;
  text-align: center;
}

.paperPlane{
  animation: pulse 1s infinite ease-in-out alternate;
}

.middle{
  text-align: center;
  font-weight: bold;
  color: #FFFFFF;
  margin-bottom: 20px;
}

.btnWrapper {
  top: 30px;
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: space-between;
  position: relative;
}

@keyframes pulse {
  from {
    transform: scale(0.95);
  }
  to {
    transform: scale(1.05);
  }
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