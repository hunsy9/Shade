const express = require('express');
const router = express.Router();
const { socketIO } = require('../service/ptySocketService')

router.post('makePtySocketNameSpace',(req,res)=>{
    console.log("들어옴")
    const io = socketIO
    console.log("aad")
    const namespace = req.body //nameSpace String
    console.log("adfad")
    const room = io.of(namespace)
    console.log(room)
})

module.exports = router;