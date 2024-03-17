import { app, protocol, BrowserWindow, ipcMain } from 'electron'
import { createProtocol } from 'vue-cli-plugin-electron-builder/lib'
import installExtension, { VUEJS3_DEVTOOLS } from 'electron-devtools-installer'
import * as path from 'path'
import { format as formatUrl } from 'url'
const isDevelopment = process.env.NODE_ENV !== 'production'

// const shell = os.platform() === "win32" ? "powershell.exe" : "bash";
let mainWindow

// let startPage = true

// Scheme must be registered before the app is ready
protocol.registerSchemesAsPrivileged([
  { scheme: 'app', privileges: { secure: true, standard: true } }
])



async function createWindow() {
  // Create the browser window.
  const win = new BrowserWindow({
    title: 'shade',
    width: 870,
    height: 500,
    minWidth:850,
    minHeight:600,
    frame: false,
    titleBarStyle: 'hiddenInset',
    backgroundColor: '#000000',
    darkTheme:true,
    icon: path.join(__dirname, '/assets/icons/png/shade1024.png'),

    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  })

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    // Load the url of the dev server if in development mode
    await win.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
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
  }

  win.on('closed', () => {
    mainWindow = null
    // e.preventDefault()
    // if(startPage){
    //   console.log("close1")
    //   mainWindow = null
    // }
    // else{
    //   console.log("close2")
    //   startPage = true;
    //   const wina = BrowserWindow.getFocusedWindow();
    //   win.setSize(1000, 600);
    //   win.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    // }
  })

  return win
}

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  console.log("all close1")
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
  // startPage = false
  const win = BrowserWindow.getFocusedWindow()
  win.setSize(1200, 720)
  win.center()
})


ipcMain.on('reset-window', () => {
  const win = BrowserWindow.getFocusedWindow()
  win.setSize(1000, 600)
  win.center()
})
