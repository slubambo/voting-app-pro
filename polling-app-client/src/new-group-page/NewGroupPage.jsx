import React, { useState } from 'react';
import { Input, Button, Form, message } from 'antd';
import axios from 'axios';
import './NewGroupPage.css'; // CSS �и��� ���� import

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

      message.success('�׷��� ���������� �����Ǿ����ϴ�!');
      setName('');
      setDescription('');
      setImageUrl('');
      setMembers([]);
      setMemberInput('');
    } catch (error) {
      console.error(error);
      message.error('�׷� ���� ����!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="new-group-container">
      <h1>�� �׷� ����</h1>
      <Form layout="vertical" onSubmitCapture={handleSubmit}>
        <Form.Item label="�׷� �̸�">
          <Input
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="��: ���͵� �׷�"
          />
        </Form.Item>

        <Form.Item label="�׷� ����">
          <Input.TextArea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="��: �Բ� �����ϴ� ���͵��Դϴ�."
            rows={4}
          />
        </Form.Item>

        <Form.Item label="�̹��� URL">
          <Input
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            placeholder="��: https://example.com/image.jpg"
          />
        </Form.Item>

        <Form.Item label="�׷� ��� �߰�">
          <Input
            value={memberInput}
            onChange={(e) => setMemberInput(e.target.value)}
            onPressEnter={handleAddMember}
            placeholder="�̸��� �Ǵ� ����� �̸�"
          />
          <Button onClick={handleAddMember} style={{ marginTop: '8px' }}>
            ��� �߰�
          </Button>
          <ul className="member-list">
            {members.map((member, index) => (
              <li key={index}>{member}</li>
            ))}
          </ul>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            �׷� ����
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default NewGroupPage;
