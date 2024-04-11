import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Template from '@/components/Template'
import Login from '@/components/Login'
import CreateAccount from '@/components/CreateAccount'
import ScheduledClass from '../components/ScheduledClass.vue'
import PromoteUser from "../components/PromoteUser.vue"
import OfferClass from '@/components/OfferClass'
import OwnerPage from '../components/OwnerPage.vue'
import ScheduleClasses from '@/components/ScheduleClasses.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
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
    },
    {
      path: '/owner-page',
      name: 'Owner Page',
      component: OwnerPage
    },
    {
      path: '/schedule_classes',
      name: 'Schedule Classes',
      component: ScheduleClasses
    }
  ]
})
