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
        path: 'project',
        name: 'add-project',
        component: () => import('@/components/organisms/ProjectForm.vue'),
        meta: { requiresAuth: true },
      },
      {
        path: 'projects/edit/:id',
        name: 'project-edit',
        component: () => import('@/components/organisms/ProjectForm.vue'),
        meta: { requiresAuth: true },
      },
      {
        path: 'kanban',
        name: 'kanban-board',
        component: () => import('@/components/organisms/ProjectList.vue'),
        meta: { requiresAuth: true },
      }
    ]
  },
];
