import { createRouter, createWebHistory } from 'vue-router'
import {routes} from "@/router/routes.ts";

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = true ;
  if(to.meta.requiresAuth && !isAuthenticated){
    next({name: 'login'});
  } else {
    next();
  }
});

export default router
