import type { RouteRecordRaw  } from "vue-router";

export const routes: RouteRecordRaw[]=[
  {
    path: '/',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
    meta: { requireAuth: false }
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('@/views/DashboardLayout.vue'), // Code-Splitting automatique
    meta: { requiresAuth: true },
    children: [
      {
        path: 'kanban',
        name: 'kanban-board',
        component: () => import('@/components/organisms/ProjectList.vue'),
        meta: { requiresAuth: true },
      }
    ]
  },
];
