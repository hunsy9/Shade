<template>
  <div class="listframe">
    <template v-for="(value, key) in getServerList" :key="key">
      <template v-if="isTrue(key)">
        <template v-for="(server, idx) in value" :key="idx">
          <div class="itembox" :style="{ backgroundColor: colors[idx % colors.length]}">
            <span class="sname">
              {{ server.server_name }}
            </span>
            <button v-if="!isAdmin">
              Info
            </button>
            <button v-if="isAdmin">
              Delete
            </button>
            <button v-if="isAdmin">
              Edit
            </button>
            <button @click="connectServer(server.server_id)">
              Connect
            </button>
          </div>
        </template>
      </template>
    </template>
  </div>
</template>
  
<script>
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: 'ServerListItem',
  data() {
    return {
      colors: ["#AFA4C6a2", "#A4C6B5a2", "#A4BEC6a2", "#C6A4A4a2"],
    }
  },
  computed: {
    ...mapState('inOrganization', {
      categ_l1: state => state.selected_categ_l1,
      categ_l2: state => state.selected_categ_l2
    }),
    ...mapState('login', {
      isAdmin: state => state.isAdmin,
    }),
    ...mapGetters('inOrganization', ['getServerList'])
  },
  methods: {
    ...mapActions('terminal', ['connectTerminal']),
    isTrue(key){
      let info = key.split(":")
      let l1 = info[1]
      let l2 = info[2]
      if(this.categ_l1 == l1 && this.categ_l2 == l2){
        return true
      }
    },
    async connectServer(serverId){
      await this.connectTerminal(serverId)
    }
  }
}
</script>

<style scoped>
.listframe{
  display: block;
  margin-top: 2rem;
  margin-left: 3rem;
  margin-right: 3rem;
}
.itembox{
  margin-bottom: 0.7rem;
  background-color: #a4bec6a2;
  border-radius: 0.4rem;
  padding: 1rem 0;
}
.sname{
  color: white;
  padding-left: 2rem;
  width: 20rem;
  margin: 0;
}
button{  
  float: right;
  background-color: #989898c2;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  color: white;
  padding: 0.1rem 1.5rem;
  margin-right: 1rem;
}
</style>