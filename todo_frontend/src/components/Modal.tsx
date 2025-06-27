import { useState } from "react";

import "../CSS/Modal.css";
import type { TaskDTO } from "../DTO/TaskDTO";

const Modal = (props: { closeModal: any, onSubmit: any, task: TaskDTO | undefined }) => {

    const { closeModal, onSubmit, task} = props;

    const [formState, setFormState] = useState(
        task ? 
        {
          title: task.title, 
          description: task.description ?? "",
        } : 
        {
            title: "", 
            description: "",
        }
    );
  const [errors, setErrors] = useState("");
  
  const validateForm = () => {
    if (formState.title) {
      setErrors("");
      return true;
    } else {
      let errorFields = [];
      for (const [key, value] of Object.entries(formState)) {
        if (!value) {
          errorFields.push(key);
        }
      }
      setErrors(errorFields.join(", "));
      return false;
    }
  };

  const handleChange = (e: any) => {
    setFormState({ ...formState, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: any) => {
    e.preventDefault();

    if (!validateForm()) return;

    onSubmit(formState);

    closeModal();
  };

  return (
    <div
      className="modal-container"
      onClick={(e: any) => {
        if (e.target.className === "modal-container") closeModal();
      }}
    >
      <div className="modal">
        <form>
          <div className="form-group">
            <label htmlFor="title">Title</label>
            <input name="title" onChange={handleChange} value={formState.title}/>
          </div>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              name="description"
              onChange={handleChange}
              value={formState.description}
            />
          </div>
          {errors && <div className="error">{`Please include: ${errors}`}</div>}
          <button type="submit" className="btn" onClick={handleSubmit}>
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default Modal;