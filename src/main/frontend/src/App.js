import React, {useState,useEffect,useCallback} from 'react';
import './App.css';
import axios from 'axios';
import Dropzone, {useDropzone} from 'react-dropzone'

const UserProfiles = () =>
{
    const [UserProfiles,setUsetProfiles] = useState([]);
    const fetchUserProfiles =  () =>
    {
        axios.get('http://localhost:8080/api/v1/user-profile').then(res => {
          console.log(res);
          setUsetProfiles(res.data);
        });
    }

    useEffect(()=>{
      fetchUserProfiles();
    },[]);

    return UserProfiles.map((userProfile,index) =>
    {
      return (
        <div key={index}>
          <br/>
          <br/>
          <h1>{userProfile.username}</h1>
          <p>{userProfile.userProfileId}</p>
          <MyDropzone {...userProfile}/>
          <br/>
        </div>
      )
    });
}

function App() {
  return (
    <div className="App">
        <UserProfiles/>
    </div>
  );
}

function MyDropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file",file);
    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,formData,
    {
      headers:{
        "Content-Type" : "multipart/form-data"
      }
    }).then(()=> 
    {console.log('file uploaded sucessfully');}).catch((err)=>{console.log(err);})
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}

export default App;
