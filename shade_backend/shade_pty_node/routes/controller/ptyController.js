const express = require('express');
const router = express.Router();
const { socketIO } = require('../service/ptySocketService')

router.post('makePtySocketNameSpace',(req,res)=>{
    const io = socketIO
    const namespace = req.body //nameSpace String
    const room = io.of(namespace)
    console.log(room)
})

module.exports = router;