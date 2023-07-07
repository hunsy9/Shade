const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  pluginOptions: {
    electronBuilder: {
      nodeIntegration: true,
    }
  },
  devServer: {
    port: 8080
  }
})