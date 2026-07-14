import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import App from '../App.vue'
import { createPinia } from 'pinia'

describe('App', () => {
  it('mounts renders properly', () => {
    const wrapper = mount(App, {
      global: {
        plugins: [createPinia()],
      },
    })
    expect(wrapper.text()).toContain('Tableau Kanban - Socle Arkéa')
  })
})
