import clang.cindex

class DataMemberVisitor:
    def __init__(self):
        self.data_members = []

    def visit_node(self, node):
        if node.kind == clang.cindex.CursorKind.FIELD_DECL:
            self.data_members.append(node)
        elif node.kind == clang.cindex.CursorKind.VAR_DECL and node.storage_class == clang.cindex.StorageClass.STATIC:
            self.data_members.append(node)

        for child in node.get_children():
            self.visit_node(child)


def detect_data_members(source_file):
    index = clang.cindex.Index.create()
    tu = index.parse(source_file)

    visitor = DataMemberVisitor()
    visitor.visit_node(tu.cursor)
    count = len(visitor.data_members)
    return visitor.data_members, count
