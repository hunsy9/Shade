import { createRouter, createWebHashHistory } from 'vue-router'
import BeforeLoginView from '../views/BeforeOrganizationView.vue'
import InOrganizationView from '../views/InOrganizationView.vue'
import ModalLogin from '@/views/beforemodal/ModalLogin.vue'
import ModalSignUp from '@/views/beforemodal/login/ModalSignUp.vue'
import ModalAddOrganization from '@/views/beforemodal/ModalAddOrganization.vue'
import ModalAddServer from '@/views/inmodal/ModalAddServer.vue'

const routes = [
  {
    path: '/',
    name: 'before',
    component: BeforeLoginView
  },
  {
    path: '/in',
    name: 'in',
    component: InOrganizationView,
  },
  //modal
  {
    path: '/modallogin',
    name: 'modallogin',
    component: ModalLogin,
  },
  {
    path: '/modalsignup',
    name: 'modalsignup',
    component: ModalSignUp,
  },
  {
    path: '/modaladdorganization',
    name: 'modaladdorganization',
    component: ModalAddOrganization,
  },
  {
    path: '/modaladdserver',
    name: 'modaladdserver',
    component: ModalAddServer,
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router