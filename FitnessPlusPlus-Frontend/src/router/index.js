import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home.vue'
import Login from '../components/Login.vue'
import CreateAccount from '../components/CreateAccount.vue'
import PromoteUser from "../components/PromoteUser.vue"
import OfferClass from '../components/OfferClass.vue'
import OwnerPage from '../components/OwnerPage.vue'
import ScheduleClasses from '../components/ScheduleClasses.vue'
import InstructorView from "../components/InstructorView.vue";
import RegisteredClass from "../components/RegisteredClass.vue";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
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
      path: '/schedule-classes',
      name: 'Schedule Classes',
      component: ScheduleClasses
    },
    {
      path: '/instructor-view',
      name: 'Instructor View',
      component: InstructorView
    },
    {
      path: '/Dashboard',
      name: 'Dashboard',
      component: RegisteredClass
    }
  ]
})
