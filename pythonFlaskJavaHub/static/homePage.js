async function useData(data) {
    document.getElementById('email').innerHTML = 'email => ' + data.email;
    document.getElementById('phoneNumber').innerHTML = 'phoneNumber => ' + data.phoneNumber;
    document.getElementById('name').innerHTML = 'name => ' + data.name;
    document.getElementById('education').innerHTML = 'education => ' + data.education;
    document.getElementById('dBTSNPEAward').innerHTML = 'dBTSNPEAward => ' + data.dBTSNPEAward;
    document.getElementById('selfDisciplineReward').innerHTML = 'selfDisciplineReward => ' + data.selfDisciplineReward;
    document.getElementById('linkGithub').innerHTML = 'linkGithub => ' + data.linkGithub;
    document.getElementById('linkYoutube').innerHTML = 'linkYoutube => ' + data.linkYoutube;
    document.getElementById('linkResume').innerHTML = 'linkResume => ' + data.linkResume;
}

async function getData() {
    let result = {};

    let response = await fetch("http://localhost:8080/api/digital-portfolio/contactMe");
    let data = await response.json();
    result.email = data.email;
    result.phoneNumber = data.phoneNumber;

    response = await fetch("http://localhost:8080/api/digital-portfolio/aboutMeTab");
    data = await response.json();
    result.name = data.name;
    result.education = data.education;
    result.dBTSNPEAward = data.dBTSNPEAward;
    result.selfDisciplineReward = data.selfDisciplineReward;

    response = await fetch("http://localhost:8080/api/digital-portfolio/skills");
    data = await response.json();
    result.linkGithub = data.linkGithub;
    result.linkYoutube = data.linkYoutube;
    result.linkResume = data.linkResume;

    console.log(result);

    useData(result);
}

getData();
