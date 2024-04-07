import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Template from '@/components/Template'

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
    }
  ]
})
