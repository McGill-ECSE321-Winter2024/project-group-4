import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Template from '@/components/Template'
import Login from '@/components/Login'
import CreateAccount from "@/components/CreateAccount";

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
    }
  ]
})
