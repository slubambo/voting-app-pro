import React, { useState } from 'react';
import { Input, Button, Form, message } from 'antd';
import axios from 'axios';
import './NewGroupPage.css'; // CSS 분리된 파일 import

const NewGroupPage = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [imageUrl, setImageUrl] = useState('');
  const [members, setMembers] = useState([]);
  const [memberInput, setMemberInput] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAddMember = () => {
    if (memberInput.trim() === '') return;
    setMembers([...members, memberInput.trim()]);
    setMemberInput('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await axios.post('/api/groups', {
        name,
        description,
        imageUrl,
        members,
      });

      message.success('그룹이 성공적으로 생성되었습니다!');
      setName('');
      setDescription('');
      setImageUrl('');
      setMembers([]);
      setMemberInput('');
    } catch (error) {
      console.error(error);
      message.error('그룹 생성 실패!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="new-group-container">
      <h1>새 그룹 생성</h1>
      <Form layout="vertical" onSubmitCapture={handleSubmit}>
        <Form.Item label="그룹 이름">
          <Input
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="예: 스터디 그룹"
          />
        </Form.Item>

        <Form.Item label="그룹 설명">
          <Input.TextArea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="예: 함께 공부하는 스터디입니다."
            rows={4}
          />
        </Form.Item>

        <Form.Item label="이미지 URL">
          <Input
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            placeholder="예: https://example.com/image.jpg"
          />
        </Form.Item>

        <Form.Item label="그룹 멤버 추가">
          <Input
            value={memberInput}
            onChange={(e) => setMemberInput(e.target.value)}
            onPressEnter={handleAddMember}
            placeholder="이메일 또는 사용자 이름"
          />
          <Button onClick={handleAddMember} style={{ marginTop: '8px' }}>
            멤버 추가
          </Button>
          <ul className="member-list">
            {members.map((member, index) => (
              <li key={index}>{member}</li>
            ))}
          </ul>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            그룹 생성
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default NewGroupPage;
