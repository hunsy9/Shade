'use strict'

import { app, protocol, BrowserWindow, ipcMain } from 'electron'
import { createProtocol } from 'vue-cli-plugin-electron-builder/lib'
import installExtension, { VUEJS3_DEVTOOLS } from 'electron-devtools-installer'
import * as path from 'path'
import { format as formatUrl } from 'url'
const isDevelopment = process.env.NODE_ENV !== 'production'

let mainWindow
let appLoginModalWindow
let addOrganModalWindow

let startPage = true

// Scheme must be registered before the app is ready
protocol.registerSchemesAsPrivileged([
  { scheme: 'app', privileges: { secure: true, standard: true } }
])

async function createWindow() {
  // Create the browser window.
  const win = new BrowserWindow({
    title: 'ssh-desktop',
    width: 1000,
    height: 600,
    titleBarStyle: 'hiddenInset',

    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  })

  const appLoginModal = new BrowserWindow({ 
    width: 400, 
    height: 500, 
    parent: win, 
    titleBarStyle: 'hidden',
    show: false,
    resizable: true,
    movavle: true,
    minimizable: false,
    maximizable: false,
    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: false
    }
  })

  const addOrganModal = new BrowserWindow({ 
    width: 625, 
    height: 375, 
    parent: win, 
    titleBarStyle: 'hidden',
    show: false,
    resizable: true,
    movavle: true,
    minimizable: false,
    maximizable: false,
    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: false
    }
  })

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    // Load the url of the dev server if in development mode
    await win.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    await appLoginModal.loadURL(process.env.WEBPACK_DEV_SERVER_URL + '#/modallogin')
    await addOrganModal.loadURL(process.env.WEBPACK_DEV_SERVER_URL + '#/modaladdorganization')
    // if (!process.env.IS_TEST) win.webContents.openDevTools()
  } else {
    createProtocol('app')
    // Load the index.html when not in development
    // win.loadURL('app://./index.html')
    win.loadURL(
      formatUrl({
        pathname: path.join(__dirname, 'index.html'),
        protocol: 'file',
        slashes: true
    }))

    appLoginModal.loadURL(`file://${__dirname}/index.html#/modallogin`)
    addOrganModal.loadURL(`file://${__dirname}/index.html#/modaladdorganization`)
  }

  win.on('closed', () => {
    if(startPage){
      console.log("close1")
      mainWindow = null
    }
    // else{
    //   console.log("close2")
    //   startPage = true;
    //   // const wina = BrowserWindow.getFocusedWindow();
    //   mainWindow.setSize(1000, 600);
    //   mainWindow.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    // }
  })

  appLoginModal.on('close', (e) => {
    e.preventDefault()
    appLoginModal.hide()
  })

  addOrganModal.on('close', (e) => {
    e.preventDefault()
    addOrganModal.hide()
  })

  appLoginModalWindow = appLoginModal
  addOrganModalWindow = addOrganModal

  return win
}

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (mainWindow === null) mainWindow = createWindow()
})

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', async () => {
  if (isDevelopment && !process.env.IS_TEST) {
    // Install Vue Devtools
    try {
      await installExtension(VUEJS3_DEVTOOLS)
    } catch (e) {
      console.error('Vue Devtools failed to install:', e.toString())
    }
  }
  mainWindow = createWindow()
})

// Exit cleanly on request from parent process in development mode.
if (isDevelopment) {
  if (process.platform === 'win32') {
    process.on('message', (data) => {
      if (data === 'graceful-exit') {
        app.quit()
      }
    })
  } else {
    process.on('SIGTERM', () => {
      app.quit()
    })
  }
}

ipcMain.on('resize-window', () => {
  startPage = false
  const win = BrowserWindow.getFocusedWindow();
  win.setSize(1200, 720);
  win.center();
});

ipcMain.on('reset-window', () => {
  const win = BrowserWindow.getFocusedWindow();
  win.setSize(1000, 600);
  win.center();
});

ipcMain.on('open-app-login-modal', () => {
  appLoginModalWindow.show();
  // appLoginModalWindow.center();
})

ipcMain.on('close-app-login-modal', () => {
  console.log("login")
  appLoginModalWindow.hide();
})

ipcMain.on('open-add-organ-modal', () => {
  addOrganModalWindow.show();
  // addOrganModalWindow.center();
})

ipcMain.on('close-add-organ-modal', () => {
  console.log("organ")
  addOrganModalWindow.hide();
})