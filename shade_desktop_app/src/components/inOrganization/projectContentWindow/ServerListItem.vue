<template>
    <div class="listframe">
        <template v-for="(value, key) in getServerList" :key="key">
            <template v-if="isTrue(key)">
                <template v-for="(server, idx) in value" :key="idx">
                    <div class="itembox" :style="{ backgroundColor: colors[idx % colors.length]}">
            <span class="sname">
              {{ server.server_name }}
            </span>
                        <!--            <button v-if="!isAdmin" @click="$emit('openModalServerInfo', server.server_name, server.server_desc)">-->
                        <!--              Info-->
                        <!--            </button>-->
                        <!--            <button v-if="isAdmin" @click="$emit('openDeleteModal', DeleteState, {server_id: server.server_id})">-->
                        <!--              Delete-->
                        <!--            </button>-->
                        <!--            <button v-if="isAdmin" @click="$emit('openModalAddServer', 'Edit Server')">-->
                        <!--              Edit-->
                        <!--            </button>-->
                        <button class="connectBtn" @click="connectServer(server.server_id, org_id, server.server_name)">
                            Connect
                        </button>
                        <div class="dot-vertical-wrapper"
                             v-on:click="openModalServerListItem(server.server_id, server.server_name, $event)">
                            <img :src="theme == 0 ? require('@/assets/dot-vertical-light.png') : require('@/assets/dot-vertical.png')"
                                 width="3" style="padding-top: 3px"/>
                        </div>
                      <ModalAdminServerListItem v-if="ModalServerListItemState" @closeModalServerListItem="ModalServerListItemState = false"/>
                    </div>
                </template>
                <div v-if="isAdmin == true" class="adminAddNewServerItemBox" @click="$emit('openModalAddServer', 'Add New Instance')">
                    <img src="@/assets/plus.png" width="25"/>
                </div>
            </template>
        </template>
    </div>
</template>

<script>
import {mapState, mapGetters, mapActions} from "vuex";
import {DeleteState} from "@/store/inOrganization";
import ModalAdminServerListItem from "@/components/inOrganization/modalComponent/ModalServerListItem";

export default {
    name: 'ServerListItem',
    components: {ModalAdminServerListItem},
    props: {
        theme: Number,
        isAdmin: Boolean
    },
    data() {
        return {
            ModalServerListItemState: false,
            DeleteState: DeleteState.DELETESERVER,
            colors: ["#A4A9C6a2", "#AFA4C6a2", "#A4C6B5a2", "#C6A4A4a2"],
        }
    },
    computed: {
        ...mapState('inOrganization', {
            categ_l1: state => state.selected_categ_l1,
            categ_l2: state => state.selected_categ_l2,
            org_id: state => state.organId,
            isAdmin: state => state.isAdmin,
        }),
        ...mapGetters('inOrganization', ['getServerList']),
    },
    methods: {
        ...mapActions('terminal', ['connectTerminal']),
        openModalServerListItem(server_id, server_name, e) {
            this.curXMousePos = e.clientX
            this.curYMousePos = e.clientY
            this.ModalServerListItemState = true
        },
        isTrue(key) {
            let info = key.split(":")
            let l1 = info[1]
            let l2 = info[2]
            if (this.categ_l1 == l1 && this.categ_l2 == l2) {
                return true
            }
        },
        async connectServer(serverId, org_id, server_name) {
            const data = {
                server_id: serverId,
                org_id: org_id,
                server_name: server_name
            }
            await this.connectTerminal(data)
        }
    }
}
</script>

<style scoped>
.listframe {
    display: block;
    margin-top: 2rem;
    margin-left: 3rem;
    margin-right: 3rem;
}

.itembox {
    display: flex;
    margin-bottom: 0.7rem;
    background-color: #a4bec6a2;
    border-radius: 0.4rem;
    padding: 1rem 0;
    animation: fadeInUp 1s ease backwards;
}

.adminAddNewServerItemBox {
    justify-content: center;
    cursor: pointer;
    display: flex;
    margin-bottom: 0.7rem;
    background-color: #2b2b2b;
    opacity: 0.67;
    border-radius: 0.4rem;
    padding: 1rem 0;
    animation: fadeInUp 1s ease backwards;
}

.adminAddNewServerItemBox:hover{
    opacity: 1;
}

.sname {
    color: var(--ModeTextColor);
    padding-left: 2rem;
    width: 20rem;
    margin: 0;
}

button {
    background-color: var(--ModeServerListItemBtnColor);
    border-radius: 0.2rem;
    border: none;
    box-shadow: 0 1px 1px 0.5px #0000002f;
    cursor: pointer;
    color: var(--ModeTextColor);
    padding: 0.1rem 1.5rem;
    margin-right: 5%;
    margin-left: 30%;
}

.connectBtn:hover {
    background-color: #9b9b9b;
}

.dot-vertical-wrapper {
    width: 15px;
    height: 20px;
    border-radius: 0.2rem;
    position: relative;
    margin-right: 3%;
    text-align: center;
    cursor: pointer;
}

.zidx {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 10;
}

.additionalTool {
    z-index: 999;
    position: absolute;
    margin-top: 10px;
    margin-right: 3%;
    background-color: #F4F4F4;
    width: 80px;
    height: 50px;
    border-radius: 10px;
}

.dot-vertical-wrapper:hover {
    background-color: rgba(224, 224, 224, 0.73);
}

@keyframes fadeInUp {
    0% {
        transform: translate(0px, 7px);
        opacity: 0;
    }
    100% {
        transform: translate(0px, 0);
        opacity: 1;
    }
}
</style>