import { createRouter, createWebHashHistory } from 'vue-router'
import BeforeLoginView from '../views/BeforeOrganizationView.vue'
import InOrganizationView from '../views/InOrganizationView.vue'

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
]

const router = createRouter({
  history: createWebHashHistory(),
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router