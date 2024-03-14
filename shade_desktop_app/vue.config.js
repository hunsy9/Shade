
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  pluginOptions: {
    electronBuilder: {
      nodeIntegration: true,
    }
  },
  devServer: {
    port: 8080
  },
  configureWebpack: {
    module: {
      rules: [
        // ... 기존 로더들 ...
        {
          test: /\.node$/,
          loader: 'raw-loader',
        },
      ],
    },
  },
})