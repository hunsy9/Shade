<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="planeWrapper">
          <img src="@/assets/paper-plane.png" width="40" height="40" class="paperPlane"/>
        </div>
        <div class="middle">
          Invite Contributor to {{organName.toUpperCase()}}_Organization
        </div>
        <div class="emailInfo">
          <label class="emailInput"> Email Address </label>
          <input type="text" v-model="addContributorEmail"/>
        </div>
        <div class="conTestLoading" v-if="sending">
          <span class="loadingLabel" v-if="sending" :class="{'fade-in-down':this.successStatus, bounce:this.failStatus}"> {{ connectingStatus }} </span>
          <LoadingSpinner v-if="isLoading" width="10px" height="10px"></LoadingSpinner>
          <img src="@/assets/testSuccess.png" :class="{'fade-in-down':this.successStatus}" width="15" height="15" v-if="successStatus === true">
          <img src="@/assets/testFail.png" :class="{bounce:this.failStatus}" width="15" height="15" v-if="failStatus === true">
        </div>
        <div class="btnWrapper">
          <button @click="$emit('closeInviteContributorModal')" class="cancel">Cancel</button>
          <button v-if="!sendMailSuccess" @click="sendMail">Send</button>
          <button v-if="sendMailSuccess" @click="closeAndReFetchContributor">Ok</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoadingSpinner from "@/components/common/LoadingSpinner";
import { mapState, mapMutations,mapActions } from "vuex";
export default {
  name: 'ModalAddContributor',
  computed: {
    ...mapState('inOrganization',{
      org_id: state=> state.organId,
      organName: state => state.organName
    }),
    ...mapState('contributorInfo', {
      admin_email: state => state.admin_contributor.contributor_email,
      contributors: state => state.contributors,
      pendings: state=>state.pending_contributors,
    })

  },
  data() {
    return {
      addContributorEmail: '',
      isLoading:false,
      sendMailSuccess:false,
      sending:false,
      connectingStatus:"Sending...",
      successStatus:false,
      failStatus:false,
    }
  },
  methods:{
    ...mapMutations('contributorInfo',['setContributors']),
    ...mapActions('inOrganization',['Contributors']),
    async closeAndReFetchContributor(){
      const data = await this.Contributors()
      if(data.admin_contributor.user_id){
        await this.setContributors(data)
      }
      this.$emit('closeInviteContributorModal')
    },
    async sendMail(){
      let email = this.addContributorEmail
      let contributors_email_list = this.contributors != null ?  Array.from(this.contributors.map(i=>i.contributor_email)) : []
      let pending_contributor_email_list = this.pendings != null ? Array.from(this.pendings.map(i=>i.contributor_email)): []
      this.isLoading = true
      this.sending = true
      this.failStatus = false
      if(email == this.admin_email || contributors_email_list.includes(email) || pending_contributor_email_list.includes(email)){
        this.connectingStatus = "User already in organization!"
        this.failStatus = true
        this.isLoading = false
        return
      }
      const dto = {
        "org_id": this.org_id,
        "user_id": null,
        "org_name": this.organName,
        "client_email" :this.addContributorEmail
      }
      let res = await fetch(`http://localhost:8081/api/mail/sendInvitationMail`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(dto)
      })
      if(res.status == 400){
        this.connectingStatus = "No User!"
        this.failStatus = true
        this.isLoading = false
      }
      else{
        this.connectingStatus = "Send Success!"
        this.sendMailSuccess = true
        this.successStatus = true
        this.isLoading = false
      }
    }
  },
  components: {
    LoadingSpinner
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
  margin-top: 230px;
  padding: 20px;
  z-index: 10;
  background-color: var(--ModeModalBackgroundColor);
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fade-in-down 0.5s;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
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
  background-color: var(--ModeInputColor);
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
  color: var(--ModeTextColor);
}

.planeWrapper{
  margin-top: 40px;
  margin-bottom: 20px;
  text-align: center;
}

.paperPlane{
  animation: pulse 1s infinite ease-in-out alternate;
}

.conTestLoading {
  text-align: right;
  float: right;
  display: flex;
  margin-top: 3px;
  margin-bottom: 3px;
  margin-right: 40px;
}

.middle{
  text-align: center;
  font-weight: bold;
  color: var(--ModeTextColor);
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
.loadingLabel {
  padding-left: 100px;
  padding-right: 0.1rem;
  color: #DDDDDD;
  font-size: 0.8rem;
}

.fade-in-down {
  animation: fade-in-down 2s ease;
  animation-fill-mode: forwards;
}

.bounce {
  animation: bounce 2s ease infinite;
}

@keyframes bounce {
  70% { transform:translateY(0%); }
  80% { transform:translateY(-15%); }
  90% { transform:translateY(0%); }
  95% { transform:translateY(-7%); }
  97% { transform:translateY(0%); }
  99% { transform:translateY(-3%); }
  100% { transform:translateY(0); }
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

@keyframes pulse {
  from {
    transform: scale(0.95);
  }
  to {
    transform: scale(1.05);
  }
}
</style>