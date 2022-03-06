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
          <MyDropzone/>
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

function MyDropzone() {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
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
