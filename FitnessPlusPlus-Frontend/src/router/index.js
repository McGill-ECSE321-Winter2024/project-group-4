import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Template from '@/components/Template'
import Login from '@/components/Login'
import CreateAccount from '@/components/CreateAccount'
import ScheduledClass from '../components/ScheduledClass.vue'
import PromoteUser from "../components/PromoteUser.vue";
import OfferClass from '@/components/OfferClass'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/template',
      name: 'Template',
      component: Template
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/new-account',
      name: 'Create Account',
      component: CreateAccount
    },
    {
      path: '/scheduled-class',
      name: 'Create Scheduled Class',
      component: ScheduledClass
    },
    {
      path: '/promote-user',
      name: 'Promote Registered User',
      component: PromoteUser
    },
    {
      path: '/offer-class',
      name: 'Offer a class',
      component: OfferClass
    }
  ]
})
