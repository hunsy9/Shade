export default ({
    namespaced: true,
  
    state: {
        organId: "",
        organName: "",
        projInfos: [
            {
                projId: "",
                projName: "MEGASTUDY12",
                category: {
                    level1: "",
                    levle2: "",
                    servers: [
                        { 
                            serverName: "MEGA_DEV_REDIS", 
                            ip: "123.123.123.123" , 
                            prev: "Develop", 
                            next: "DBMS", 
                        },
                    ]
                }
                
            },
            {
                projId: "",
                projName: "MEGASTUDY22",
                servers: [
                    { 
                        serverName: "MEGA_DEV_REDIS", 
                        ip: "123.123.123.123" , 
                        prev: "Develop", 
                        next: "DBMS", 
                    },
                ]
            },
        ]
    },
    getters: {
    },
    mutations: {
        setData(state, organName){
            state.organName = organName;
        }
    },
    actions: {
    },
    modules: {
    },
})